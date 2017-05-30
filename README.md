# VersionEye plug-in for [Kobalt](http://beust.com/kobalt/home/index.html)

[![License (3-Clause BSD)](https://img.shields.io/badge/license-BSD%203--Clause-blue.svg?style=flat-square)](http://opensource.org/licenses/BSD-3-Clause) [![release](http://github-release-version.herokuapp.com/github/ethauvin/kobalt-versioneye/release.svg?style=flat)](https://github.com/ethauvin/kobalt-versioneye/releases/latest) [![Dependency Status](https://www.versioneye.com/user/projects/58ff729f6ac171425cd00acf/badge.svg?style=flat-square)](https://www.versioneye.com/user/projects/58ff729f6ac171425cd00acf) [![Build Status](https://travis-ci.org/ethauvin/kobalt-versioneye.svg?branch=master)](https://travis-ci.org/ethauvin/kobalt-versioneye) [![CircleCI](https://circleci.com/gh/ethauvin/kobalt-versioneye/tree/master.svg?style=shield)](https://circleci.com/gh/ethauvin/kobalt-versioneye/tree/master) [![Download](https://api.bintray.com/packages/ethauvin/maven/kobalt-versioneye/images/download.svg) ](https://bintray.com/ethauvin/maven/kobalt-versioneye/_latestVersion)

[![VersionEye Logo](src/site/images/VersionEyeLogo.png)](https://www.versioneye.com)

The plug-in will create and update projects on [VersionEye](https://www.versioneye.com/), a service that notifies you about outdated dependencies, security vulnerabilities and license violations.

To use the plug-in include the following in `Build.kt` file:

```kotlin
import net.thauvin.erik.kobalt.plugin.versioneye.*

val bs = buildScript {
    plugins("net.thauvin.erik:kobalt-versioneye:")
}

val p = project {
    name = "example"
    group = "com.example"
    artifactId = name
    version = "0.1"

    versionEye {

    }
}
```
[View Example](https://github.com/ethauvin/kobalt-versioneye/blob/master/example/kobalt/src/Build.kt)

To create or update your project on VersionEye, you will need an API key. If you are [signed up](https://www.versioneye.com/signup), you can find your API Key [here](https://www.versioneye.com/settings/api):

[![VersionEye API Key](src/site/images/VersionEyeApiKey.png)](https://www.versioneye.com/settings/api)

To create your project on VersionEye simply use your API Key as follows

```bash
./kobaltw -Dversioneye.ApiKey=YOUR_API_KEY_HERE versionEye
```

This will instruct the plug-in to create and update your project on VersionEye. Your API Key will automatically be saved in the`local.properties` file.

Upon running the above command the plug-in will output something like:

![Example Report](src/site/images/ExampleReport.png)

The report is based on the *Traffic Light* concept:

1. Green items are clear.
2. Yellow items may require some attention.
3. Red items will cause the build to fail.

By default the plug-in is configured to only fail on known security vulnerabilities.

## Global Configuration

You can configure the VersionEye **API Key** and **Project Key** (if you already have one) in one of two ways.

Using the command line:

``` bash
./kobaltw -Dversioneye.ApiKey=YOUR_API_KEY -Dversionkey.projectKey=YOUR_PROJECT_KEY versionEye
```
These keys will be save automatically in the `local.properties` file.

In the `local.properties` file:

```
versioneye.ApiKey=YOUR_API_KEY
versioneye.projectKey=YOUR_PROJECT_KEY
```
## Task Configuration

Parameters are configurable in the `versionEye` task:

```
versionEye {
    baseUrl = "https://www.versioneye.com/"
    colors = true
    name = ""
    org = ""
    quiet = false
    team = ""
    verbose = true
    visibility = "public"

    failOn(Fail.securityCheck)
}
```

The values are:

| Value        | Description                                                                                                            | Default                       |
| :----------- | :--------------------------------------------------------------------------------------------------------------------- | :---------------------------- |
| `baseUrl`    | For VersionEye Enterprise VM to use your own domain, e.g. `https://versioneye.my-company.com/`                         | `https://www.versioneye.com/` |
| `colors`     | To enable (`true`) or disable (`false`) colors in the plug-in output.                                                  | `true`                        |
| `name`       | The name of the VersionEye project, if none is specified the Kobalt project name will be used.                         | *none*                        |
| `quiet`      | To completely disable output from the plug-in.                                                                         | `false`                       |
| `org`        | The VersionEye organization, if any.                                                                                   | *none*                        |
| `pom`        | Generate a [pom.xml](https://maven.apache.org/pom.html) for the project. See [Private vs. Public](#private-vs-public). | `false`                       |
| `team`       | The VersionEye team, if any.                                                                                           | *none*                        |
| `verbose`    | To enable or disable extra information in the plug-in output.                                                          | `true`                        |
| `visibility` | To set the project's visibility on VersionEye, either `public` or `private`                                            | `public`                      |

Some of the parameters can be controlled temporarily from the command line, as follows:

```bash
./kobaltw -Dve.colors=false versionEye
./kobaltw -Dve.verbose=false versionEye
./kobaltw -Dve.quiet=true versionEye

./kobaltw -Dve.colors=false -Dve.verbose=false versionEye
```

## Failures Configuration

The `failOn` directive can be used to trigger specific failures:

```
versionEye {
    failOn(Fail.securityCheck, Fail.licensesCheck)
}
```

| Fail On                     | Description                                                                                                                                                             |
| :-------------------------- | :---------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `Fail.dependenciesCheck`    | Will triggered a failure on outdated dependencies.                                                                                                                      |
| `Fail.licensesUnknownCheck` | Will trigger a failure on unknown licenses.                                                                                                                             |
| `Fail.licensesCheck`        | Will trigger a failure on licenses whitelist violations. Licenses and components whitelists can be configured on the [VersionEye](https://www.versioneye.com/) website. |
| `Fail.securityCheck`        | Will trigger a failure on known security vulnerabilities, on by default.                                                                                                |

## Private vs. Public

By default, projects created using the VersionEye API are private. If your project is hosted on [GitHub](https://github.com) or [BitBucket](https://bitbucket.org) and would like to make it public on VersionEye. The plug-in can generate a [pom](https://maven.apache.org/pom.html) file compatible with VersionEye, as follows:

```
versionEye {
    pom = true
...
}
```

and to generate the pom file, but **not** create a new project on VersionEye:

```bash
./kobaltw -Dve.create=false versionEye
```

Be sure to commit `pom.xml` on GitHub or BitBucket, and then import your project at:

  * https://www.versioneye.com/projects/new

Finally, configure your [project key](#global-configuration).