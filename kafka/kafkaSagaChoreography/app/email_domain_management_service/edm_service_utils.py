from app.zzz_helpers.general_utils import get_current_timestamp

def validate_event_authenticate_domain_success(event):
    return event.get("eventType") == "AuthenticateDomain.Success.DB"

def authenticate_domain_success(event):
    # Since we have successfully authenticated the domain- we need to now create the event for NotificationService to notify the client
    new_event = {
        "sagaId": event["sagaId"],
        "eventType": "NotificationRequest.AuthenticateDomain",
        "timestamp": get_current_timestamp(),
        "payload": {
            "notifyMethod": "email",
            "sender": "admin@kafkaSagaChoreograpy.com",
            "receiver": event["payload"]["requesterEmail"],
            "message": f"Your domain {event["payload"]["domain"]} was authenticated successfully",   
        }
    } 
    
    return new_event

def validate_event_authenticate_domain_failed(event):
    return event.get("eventType") == "AuthenticateDomain.Fail.SendGrid"

def authenticate_domain_failed(event):
    # Since we have failed to authenticate the domain- we need to now create the event for NotificationService to notify the client
    new_event = {
        "sagaId": event["sagaId"],
        "eventType": "NotificationRequest.AuthenticateDomain",
        "timestamp": get_current_timestamp(),
        "errorService": event["errorService"],
        "payload": {
            "notifyMethod": "email",
            "sender": "admin@kafkaSagaChoreograpy.com",
            "receiver": event["payload"]["requesterEmail"],
            "message": f"Your domain {event["payload"]["domain"]} failed to authenticate. Please try again.",   
        }
    } 
    
    return new_event
