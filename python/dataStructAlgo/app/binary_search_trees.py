class TreeNode:
    def __init__(self, val=0):
        self.val = val
        self.left = None
        self.right = None
    
def insert(root, val):
    if not root:
        return TreeNode(val)
    if val < root.val:
        root.left = insert(root.left, val)
    else:
        root.right = insert(root.right, val)
    return root

# In-Order: left, root, right - gives value in a sorted order
def in_order_traversal(root):
    if not root:
        return
    if root.left:
        in_order_traversal(root.left)
    print(root.val, end=", ")
    if root.right:
        in_order_traversal(root.right)
      
# Pre-Order: root, left, right - great for cloning tree
def pre_order_traversal(root):
    if not root:
        return
    print(root.val, end=", ")
    if root.left:
        in_order_traversal(root.left)
    if root.right:
        in_order_traversal(root.right)

# Post-Order: left, right, root - used to delete trees or evaluate math expressions
def post_order_traversal(root):
    if not root:
        return
    if root.left:
        in_order_traversal(root.left)
    if root.right:
        in_order_traversal(root.right)
    print(root.val, end=", ")
  
root = TreeNode(5)
root = insert(root, 3)
root = insert(root, 2)
root = insert(root, 4)
root = insert(root, 1)
root = insert(root, 5)
root = insert(root, 9)
root = insert(root, 7)
root = insert(root, 11)

in_order_traversal(root) # 1, 2, 3, 4, 5, 5, 7, 9, 11, 
print()
pre_order_traversal(root) # 5, 1, 2, 3, 4, 5, 7, 9, 11,
print()
post_order_traversal(root) # 1, 2, 3, 4, 5, 7, 9, 11, 5,
print()

def search_key(root, val):
    if root is None:
        return False
    elif root.val == val:
        return True
    elif val < root.val:
        return search_key(root.left, val)
    elif val > root.val:
        return search_key(root.right, val)
    else:
        return False
    
print(search_key(root, 1)) # True
print(search_key(root, 6)) # False

def max_depth(root):
    if root is None:
        return 0
    else:
        return 1 + max(max_depth(root.left), max_depth(root.right))
    
print(max_depth(root)) # 4
root = insert(root, 13)
print(max_depth(root)) # 5
root = insert(root, 0)
root = insert(root, -1)
print(max_depth(root)) # 6
    
