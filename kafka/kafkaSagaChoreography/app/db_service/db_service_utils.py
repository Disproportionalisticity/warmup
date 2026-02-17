from app.zzz_helpers.general_utils import get_current_timestamp

def validate_event_authenticate_domain_db(event):
    return event.get("eventType") == "AuthenticateDomain.Success.Route53"

def authenticate_domain_db(event):
    # Since we successfully saved to DB, we don't need to add any info
    # Additionally, we can delete the responses from SendGrid and Route53 from payload, since they are no longer needed
    del event["payload"]["sendgridResponse"]
    del event["payload"]["Route53Response"] 

    # Update metadata
    event["timestamp"] = get_current_timestamp()
    event["eventType"] = "AuthenticateDomain.Success.DB"
    
    return event

def validate_event_authenticate_domain_db_should_fail(event):
    return event.get("futureError", {}).get("errorService") == "DB"

def authenticate_domain_db_should_fail(event):
    # Simulate DB error
    event["errorService"] = "DB"
    
    # Remove error from payload (to make it easier for Kafka)
    # For this case we need to keep the "errorServiceCompensation in case it is present"
    del event["futureError"]["errorService"]
    
    # check if futureError is empty, then delete it alltogether
    if event["futureError"]:
        del event["futureError"]

    # Update metadata
    event["timestamp"] = get_current_timestamp()
    event["eventType"] = "AuthenticateDomain.Fail.DB"
    
    return event