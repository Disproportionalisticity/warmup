import json
from datetime import UTC, datetime
from functools import partial
from typing import Annotated, Literal
from uuid import UUID, uuid4

from pydantic import (
    BaseModel,
    ConfigDict,
    EmailStr,
    Field,
    HttpUrl,
    SecretStr,
    ValidationError,
    computed_field,
    field_validator,
    model_validator,
)


class User(BaseModel):
    # populate_by_name=True allows Pydantic to load by field name as well as by alias when loading data
    # strict=True will no longer convert types (number int to int)
    # extra="allow" will allow extra fields to be added during the loading data phase. By default Pydantic ignore those
    # extra="forbid" if you want an error to be raised if there are extra fields
    # validate_assignment=True will enable type revalidation if we change the data after we instantiated the object
    # frozen=True makes the object immutable
    model_config = ConfigDict(populate_by_name=True, strict=True, extra="allow", validate_assignment=True, frozen=True)
    
    # alias allows for fields with key id to be treated as uid when loading data
    uid: UUID = Field(alias="id", default_factory=uuid4)
    username: Annotated[str, Field(alias="un", min_length=3, max_length=10)]
    email: EmailStr
    password: SecretStr
    confirm_password: SecretStr
    website: HttpUrl | None = None
    age: Annotated[int, Field(ge=13, le=130)]
    verified_at: datetime | None = None
    bio: str = ""
    is_active: bool = True
    first_name: str = ""
    last_name: str = ""
    follower_count: int = 0 
    
    # below method represents an "after validation", meaning it will be executed after Pydantic has done type checking and basic validation
    @field_validator('username')
    @classmethod
    def validate_username(cls, v: str) -> str:
        if not v.replace("_", "").isalnum():
            raise ValueError("Username must be alphanumeric (underscores allowed)")
        return v.lower()
    
    # in order for it to be executed before (pre-process the data), we need to specify the mode="before"
    @field_validator("website", mode="before")
    @classmethod
    def add_https(cls, v: str | None) -> str | None:
        if v and not v.startswith(("http://", "https://")):
            return f"https://{v}"
        return v
    
    @model_validator(mode="after")
    def password_match(self) -> "User":
        if self.password != self.confirm_password:
            raise ValueError("Passwords do not match")
        return self
    
    @computed_field
    @property
    def display_name(self) -> str:
        if self.first_name and self.last_name:
            return f"{self.first_name} {self.last_name}"
        return self.username
    
    @computed_field
    @property
    def is_influencer(self) -> bool:
        return self.follower_count >= 1000


user = User(
    username="User_Name1", 
    email="user@example.com",
    age=15,
    password=SecretStr("secret123"),
    confirm_password=SecretStr("secret123"),
    first_name="someUser",
    last_name="someName",
    follower_count=1000,
    website="test.com"  # type: ignore
)

user.bio = "Python developer"

# print(user)
# print(user.password.get_secret_value())

# # dump as Python Dict
# print(user.model_dump())
# # dump as JSON
# print(user.model_dump_json(indent=2))

# after initial object creation, if you modify the values of a class- it will not be type validated by default
# user.bio = 123

# print(user)

try:
    # in this form, only username and email will throw an error, as uid will be automatically converted
    # a numeric string will be a valid type for it as long as it can be converted, otherwise it will throw an error
    # 123 - valid, "123" - valid, "abc" - invalid, "123t" - invalid
    # anotherUser = User(uid="123t", username=123, email=True)
    anotherUser = User(username="username", age=14, email="user@example.com", password=SecretStr("key"), confirm_password=SecretStr("key"))
except ValidationError as e:
    print(e)




class Comment(BaseModel):
    content: str
    author_email: EmailStr
    likes: int = 0

class BlogPost(BaseModel):
    title: Annotated[str, Field(min_length=1, max_length=200)]
    content: Annotated[str, Field(min_length=10)]
    author: User
    view_cound: int = 0
    is_published: bool = False
    # creates a new list for each instance of the object
    tags: list[str] = Field(default_factory=list)
    # datetime = datetime.now(tz=UTC) - this will be called once, when the class is defined, not every time a new instance of the class is created
    # possible solution using lambda datetime = Field(default_factory=lambda: datetime.now(UTC))
    # the used solution uses partial that takes as first param the function name and each subsequent param- the param of the initial function
    # it returns an unexecuted function
    created_at: datetime = Field(default_factory=partial(datetime.now, tz=UTC))
    status: Literal["draft", "published", "archived"] = "draft"
    slug: Annotated[str, Field(pattern=r"^[a-z0-9-]+$")]
    comments: list[Comment] = Field(default_factory=list)
    

post_data = {
    "title": "Some random title",
    "content": "Some random content",
    "slug": "some-slug",
    "author": {
        "username": "Username1",
        "email": "randomEmail@example.com",
        "age": 18,
        "password": "key",
        "confirm_password": "key"
    },
    "comments": [
        {
            "content": "First comment",
            "author_email": "firstCommentEmail@example.com",
            "likes": "13"
        },
        {
            "content": "Second comment",
            "author_email": "secondCommentEmail@example.com",
            "likes": "25"
        },
    ]
}

# post = BlogPost(**post_data)
post = BlogPost.model_validate(post_data)

# print(post.model_dump_json(indent=2))


user_data = {
    # "id": "abracadabra",
    "un": "ExUserN",
    "email": "email@domain.com", 
    "age": 39,
    "password": "secret123",
    "confirm_password": "secret123",
    "notes": "ransom note"
}

user = User.model_validate(user_data)

# by_alias=True will dump using alias names instead of field names
# include={""} will show only specified fields
# exclude={""} will remove specified fields
# print(user.model_dump_json(indent=2, by_alias=True, include={"username", "email", "password"}, exclude={"password", "confirm_password"}))

user = User.model_validate_json(json.dumps(user_data))

print(user.model_dump_json(indent=2))