import os.path
import sys

connect("admin","Welcome1","t3://localhost:7001")
adm_name=get('AdminServerName')
sub_deployment_name="TestJMSSubdeployment"
jms_module_name="TestJMSModule"
queue_name="IncomingQueue"
factory_name="TestConnectionFactory"
jms_server_name="TestJMSServer"


def createJMSServer(adm_name, jms_server_name):
        cd('/JMSServers')
        if (len(ls(returnMap='true')) == 0):
            print 'No JMS Server found, creating ' +  jms_server_name
            cd('/')
            cmo.createJMSServer(jms_server_name)
            cd('/JMSServers/'+jms_server_name)
            cmo.addTarget(getMBean("/Servers/" + adm_name))


def createJMSModule(jms_module_name, adm_name, sub_deployment_name):
        print "Creating JMS module " + jms_module_name
        cd('/JMSServers')
        jms_servers=ls(returnMap='true')
        cd('/')
        module = create(jms_module_name, "JMSSystemResource")
        module.addTarget(getMBean("Servers/"+adm_name))
        cd('/SystemResources/'+jms_module_name)
        module.createSubDeployment(sub_deployment_name)
        cd('/SystemResources/'+jms_module_name+'/SubDeployments/'+sub_deployment_name)
        cmo.addTarget(getMBean("/JMSServers/TestJMSServer"))

        list=[]
        for i in jms_servers:
                list.append(ObjectName(str('com.bea:Name='+i+',Type=JMSServer')))
        set('Targets',jarray.array(list, ObjectName))

def getJMSModulePath(jms_module_name):
        jms_module_path = "/JMSSystemResources/"+jms_module_name+"/JMSResource/"+jms_module_name
        return jms_module_path
 
def createJMSTEMP(jms_module_name,jms_temp_name):
        jms_module_path= getJMSModulePath(jms_module_name)
        cd(jms_module_path)
        cmo.createTemplate(jms_temp_name)
        cd(jms_module_path+'/Templates/'+jms_temp_name)
        cmo.setMaximumMessageSize(20)
 
def createJMSQueue(jms_module_name,jms_queue_name):
        print "Creating JMS queue " + jms_queue_name
        jms_module_path = getJMSModulePath(jms_module_name)
        cd(jms_module_path)
        cmo.createQueue(jms_queue_name)
        cd(jms_module_path+'/Queues/'+jms_queue_name)
        cmo.setJNDIName("jms/" + jms_queue_name)
        cmo.setSubDeploymentName(sub_deployment_name)

def createJMSFactory(jms_module_name,jms_fact_name):
        print "Creating JMS connection factory " + jms_fact_name
        jms_module_path = getJMSModulePath(jms_module_name)
        cd(jms_module_path)
        cmo.createConnectionFactory(jms_fact_name)
        cd(jms_module_path+'/ConnectionFactories/'+jms_fact_name)
        cmo.setJNDIName("jms/" + jms_fact_name)
        cmo.setSubDeploymentName(sub_deployment_name)



edit()
startEdit()

print "Server name: "+adm_name

createJMSServer(adm_name,jms_server_name)
createJMSModule(jms_module_name,adm_name,sub_deployment_name)
createJMSFactory(jms_module_name,factory_name)
createJMSQueue(jms_module_name,queue_name)

save()
activate(block="true")
disconnect() 