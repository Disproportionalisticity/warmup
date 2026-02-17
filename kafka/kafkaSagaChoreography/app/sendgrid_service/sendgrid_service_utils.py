from app.zzz_helpers.general_utils import get_current_timestamp

def validate_event_authenticate_domain_sendgrid(event):
    return event.get("eventType") == "AuthenticateDomain.Init"

def authenticate_domain_sendgrid(event):
    # Simulated SendGrid response
    event["payload"]["sendgridResponse"] = {
        "subUser": "subUserID1",
        "keySet": "valueSet"
    }

    # Update metadata
    event["timestamp"] = get_current_timestamp()
    event["eventType"] = "AuthenticateDomain.Success.SendGrid"
    
    return event

def validate_event_authenticate_domain_sendgrid_should_fail(event):
    return event.get("futureError", {}).get("errorService") == "SendGrid"

def authenticate_domain_sendgrid_should_fail(event):
    # Simulate SendGrid error
    event["errorService"] = "SendGrid"
    
    # Remove error from payload (to make it easier for Kafka)
    del event["futureError"]

    # Update metadata
    event["timestamp"] = get_current_timestamp()
    event["eventType"] = "AuthenticateDomain.Fail.SendGrid"
    
    return event

def validate_event_authenticate_compensate_domain_sendgrid(event):
    return event.get("eventType") == "AuthenticateDomain.Fail.Route53"

def authenticate_compensate_domain_sendgrid(event):
    # Simulate SendGrid response
    event.setdefault("compensation", {})["sendGrid"] = "success"

    # Update metadata
    event["timestamp"] = get_current_timestamp()
    event["eventType"] = "AuthenticateDomain.Fail.SendGrid"
    
    return event

def validate_event_authenticate_compensate_domain_sendgrid_should_fail(event):
    return event.get("futureError", {}).get("errorServiceCompensation") == "SendGrid"

def authenticate_compensate_domain_sendgrid_should_fail(event):
    # Simulate SendGrid response
    event.setdefault("compensation", {})["sendGrid"] = "fail"
    
    # Remove error from payload (to make it easier for Kafka)
    # For this case we need to keep the "errorServiceCompensation in case it is present"
    del event["futureError"]["errorServiceCompensation"]
    
    # check if futureError is empty, then delete it alltogether
    if event["futureError"] == {}:
        del event["futureError"]

    # Update metadata
    event["timestamp"] = get_current_timestamp()
    event["eventType"] = "AuthenticateDomain.Fail.SendGrid.Compensation"
    
    return event

def validate_event_authenticate_domain_sendgrid_fail_transaction(event):
    return event.get("futureError", {}).get("errorTransaction") == "SendGrid"