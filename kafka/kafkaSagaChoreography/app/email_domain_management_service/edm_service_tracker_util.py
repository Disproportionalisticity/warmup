def get_status_based_on_event(event_type):
    match event_type:
        case "AuthenticateDomain.Init":
            result = "init"
        case "AuthenticateDomain.Success.SendGrid":
            result = "in_progress"
        case "AuthenticateDomain.Success.Route53":
            result = "in_progress"
        case "AuthenticateDomain.Success.DB":
            result = "authenticated"
        case "AuthenticateDomain.Fail.SendGrid":
            result = "failed"
        case "AuthenticateDomain.Fail.Route53":
            result = "failed"
        case "AuthenticateDomain.Fail.DB":
            result = "failed"
            
    return result

def create_initial_mongo_document(event_type, domain, error_service, compensation):
    payload = {
        "domainName": domain,
        "status": get_status_based_on_event(event_type)
    }
    
    if error_service != "":
        payload["errorService"] = error_service
    
    if compensation != "":
        payload["compensation"] = compensation
        
    return payload
    
def process_event(event, mongo_collection):
    # this is what we are interested in from the event
    saga_id = event.get("sagaId")
    event_type = event.get("eventType")
    domain = event.get("payload").get("domain")
    
    error_service = event.get("errorService", "")
    compensation = event.get("compensation", "")
    
    mongo_document = create_initial_mongo_document(event_type, domain, error_service, compensation)
    mongo_collection.update_one(
        {"sagaId": saga_id},
        {"$set": mongo_document},
        upsert=True
    )
    print(f"Saved event: sagaId: {saga_id}, payload: {mongo_document}")