from app.email_domain_management_service.edm_service_init import start_auth_flow
import sys

if __name__ == "__main__":
        flowName = sys.argv[1] if len(sys.argv) > 1 else "happyFlow"
        validFlows = {"happyFlow", "failSendGrid", "failRoute53", "failDB", "failRoute53_failSendGridCompensation_DLQ", "sendGridTransactionFail"}
        flowName = flowName if flowName in validFlows else "happyFlow"
        start_auth_flow(flowName)
    
"""
all the possbile payloads are stored in app/possible_payloads.json

the param for start_auth_flow determines where the error is going to be

possible params are:

success:

        happyFlow -> 
                all services are successful
                notificationService sends a message of "Domain Authenticated Successfully."
                final domain status in MongoDB - authenticated

simple failure:

        failSendGrid -> 
                SendGrid fail
                notificationService sends a message of "Not able to authenticate the domain. Try again."
                final domain status in MongoDB - failed
                
        failRoute53 -> 
                SendGrid success, Route53 fail, SendGrid compensation success
                notificationService sends a message of "Not able to authenticate the domain. Try again."
                final domain status in MongoDB - failed
                
        failDB -> 
                SendGrid success, Route53 Success, DB fail, Route53 compensation success, SendGrid compensation success
                notificationService sends a message of "Not able to authenticate the domain. Try again."
                final domain status in MongoDB - failed

failure at compensation:

        failRoute53_failSendGridCompensation_DLQ ->
                SendGrid Success, Route53 Fail, SendGrid Compensate fail
                This case requires manual intervention, so we send this event to DLQ to be manually investigated.
                Meanwhile, we do not send the Notification to our client, since the domain is not authenticated and they cannot retry until we manually fix it
                Domain final status- manual_fix_required

failure at Transaction Commit (beware that this step will require to restart docker alltogether, since manual cleanup is messy and not worth it for the example)
        
        sendGridTransactionFail -> 
                SendGrid fails to commit transaction, and it will be an infinite loop in which it reads the same message
"""