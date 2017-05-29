# Build an Oracle Application Container Cloud Cache based application

Check [the blog](https://community.oracle.com/community/cloud_computing/oracle-cloud-developer-solutions/blog/2017/05/29/build-oracle-app-container-cloud-cache-based-application-cicd-using-oracle-developer-cloud) for details

## Create an Application Container Cloud Cache

- Follow the [documentation](http://docs.oracle.com/en/cloud/paas/app-container-cloud/cache/creating-cache-service.html)
- Use the name `test-cache` or update the [code](https://github.com/abhirockzz/app-container-cloud-cache-example/blob/master/src/main/java/com/oracle/cloud/acc/cache/dcs/CacheREST.java#L20) and [configuration](https://github.com/abhirockzz/app-container-cloud-cache-example/blob/master/deployment.json#L6) with the new name

## Build

- `git clone https://github.com/abhirockzz/app-container-cloud-cache-example.git`
- `mvn clean install` - creates `acc-cache-dcs-1.0-dist.zip` in `target` directory

## Deploy

- Use Developer Cloud - read [the blog](https://community.oracle.com/community/cloud_computing/oracle-cloud-developer-solutions/blog/2017/05/29/build-oracle-app-container-cloud-cache-based-application-cicd-using-oracle-developer-cloud#jive_content_id_Oracle_Developer_Cloud)
- Use Application Container Cloud [console](http://docs.oracle.com/en/cloud/paas/app-container-cloud/csjse/exploring-application-deployments-page.html#GUID-5E4472B1-F5C6-4556-908C-D76C4C14FC60)
- Use Application Container Cloud [REST APIs](http://docs.oracle.com/en/cloud/paas/app-container-cloud/apcsr/op-paas-service-apaas-api-v1.1-apps-%7BidentityDomainId%7D-post.html)
- Use Application Container Cloud [PSM APIs](https://docs.oracle.com/en/cloud/paas/java-cloud/pscli/accs-push.html)