## vaultclient-java release

### Introduction

The artifacts of vaultclient-java project are stored in Nexus with the following metadata
```
Group ID : com.scality
artifact ID: vaultclient-java
```
We separate dev packages and released packages by having ``-SNAPSHOT`` suffix or not
in version, every git push operation will publish dev packages to [Nexus Snapshot Repositories](https://oss.sonatype.org/#view-repositories;snapshots~browsestorage),
dev snapshot packages can be overwritten all the time and 
can be directly used for testing in other projects.

But when we want to release a package, we will need to perform the following steps:

### Release Process

1. Check the project version in [build.gradle](../build.gradle#L19) and [vaultclient/build.gradle](../vaultclient/build.gradle#L17),
if the version specified is not the one you want to release, then make a PR to update it.


2. Run release workflow in the [github actions release page](https://github.com/scality/vaultclient-java/actions/workflows/release.yml).


3. Once the release workflow has successfully completed, proceed to Nexus Repository Manager to check if the released package is present.
    
   > Nexus Repo link: https://oss.sonatype.org/#nexus-search;quick~com.scality
   > 
   > For login credentials, they are stored in github secrets or can be obtained by emailing Object Squad <object-squad@scality.com>
    
4. If the release workflow fails at ``Gradle Publish Release to Nexus`` step, and raises error `Wrong number of received repositories in state 'open'. Expected 1, received 2`.

   > It's because that there are old packages in staging repositories from this project or OSIS project that has not yet been closed. Gradle expects to have only one package in staging repositories that is waiting to be released.
   > 
   > Please proceed to [Nexus Stating Repositories](https://oss.sonatype.org/#stagingRepositories) and drop 
   > all the open staging repositories. Then, re-run the github action release workflow. 

