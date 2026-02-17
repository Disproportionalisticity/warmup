from app.zzz_helpers.general_utils import get_current_timestamp

def validate_event_authenticate_domain_route53(event):
    return event.get("eventType") == "AuthenticateDomain.Success.SendGrid"

def authenticate_domain_route53(event):
    # Simulated Route53 response
    event["payload"]["Route53Response"] = {
        "keySet": "valueSet"
    }

    # Update metadata
    event["timestamp"] = get_current_timestamp()
    event["eventType"] = "AuthenticateDomain.Success.Route53"
    
    return event

def validate_event_authenticate_domain_route53_should_fail(event):
    return event.get("futureError", {}).get("errorService") == "Route53"

def authenticate_domain_route53_should_fail(event):
    # Simulate Route53 error
    event["errorService"] = "Route53"
    
    # Remove error from payload (to make it easier for Kafka)
    # For this case we need to keep the "errorServiceCompensation in case it is present"
    del event["futureError"]["errorService"]
    
    # check if futureError is empty, then delete it alltogether
    if event["futureError"] == {}:
        del event["futureError"]

    # Update metadata
    event["timestamp"] = get_current_timestamp()
    event["eventType"] = "AuthenticateDomain.Fail.Route53"
    
    return event

def validate_event_authenticate_compensate_domain_route53(event):
    return event.get("eventType") == "AuthenticateDomain.Fail.DB"

def authenticate_compensate_domain_route53(event):
    # Simulate Route53 response
    event.setdefault("compensation", {})["route53"] = "success"

    # Update metadata
    event["timestamp"] = get_current_timestamp()
    event["eventType"] = "AuthenticateDomain.Fail.Route53"
    
    return event
