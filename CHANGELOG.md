# Changelog

## [1.0.3](https://github.com/ahincho/nova-java-16-spring-boot-gradle-plugin/compare/v1.0.2...v1.0.3) (2026-09-27)


### Bug Fixes

* **deps:** add the renamed starters at 2.0.0 and move to Spring Boot 4.0.8 ([d2bfc95](https://github.com/ahincho/nova-java-16-spring-boot-gradle-plugin/commit/d2bfc958bc61864bda913e0ba0fa903587164f38))
* **deps:** add the renamed starters at 2.0.0 and move to Spring Boot 4.0.8 ([3741095](https://github.com/ahincho/nova-java-16-spring-boot-gradle-plugin/commit/3741095a15e19fbc1e51a75e0a5856e0f3f745e1))
* **deps:** raise httpclient5 to 5.6.4 for CVE-2026-71290 ([10704bd](https://github.com/ahincho/nova-java-16-spring-boot-gradle-plugin/commit/10704bdb6ca0f272892f822ac4d27e56244c5bfe))

## [1.0.2](https://github.com/ahincho/nova-java-16-spring-boot-gradle-plugin/compare/v1.0.1...v1.0.2) (2026-09-27)


### Bug Fixes

* **plugin:** replace dead umbrella starter coordinates with the two sub-starters ([fe8f1db](https://github.com/ahincho/nova-java-16-spring-boot-gradle-plugin/commit/fe8f1dbc557f81c8db6d727739ab9005c2bc9dfc))


### Documentation

* add a README and adopt EPL-2.0 ([b9caa83](https://github.com/ahincho/nova-java-16-spring-boot-gradle-plugin/commit/b9caa838946cef9c04b3b69bf5017e845da6f758))

## [1.0.1](https://github.com/ahincho/nova-java-spring-boot-gradle-plugin/compare/v1.0.0...v1.0.1) (2026-07-13)


### Bug Fixes

* **ci:** add component + skip-snapshot + manifest-file (mask-utils pattern) ([bccba1a](https://github.com/ahincho/nova-java-spring-boot-gradle-plugin/commit/bccba1a901e8235bb8e1152b26f9b59137c6b038))
* **ci:** add last-release-sha, include-component-in-tag: false, release-type: java to top-level config; pass manifest-file in wrapper ([ae519c7](https://github.com/ahincho/nova-java-spring-boot-gradle-plugin/commit/ae519c70e7de595174c5863bb1e123bc4e23131e))

## 1.0.0 (2026-07-10)


### Features

* **ci:** migrate to release-please + tag-based publish flow (NOVA-SEMVER-13) ([a9028fb](https://github.com/ahincho/nova-java-spring-boot-gradle-plugin/commit/a9028fb419f39224ace3f267eac8da8e268a99e6))
* **gradle:** add GPG signing plugin for Maven Central publishing (NOVA-SEMVER-10) ([435f7bb](https://github.com/ahincho/nova-java-spring-boot-gradle-plugin/commit/435f7bbd2247a9b70db346a7d49956316c836bfc))
* **gradle:** enable Local Build Cache and Configuration Cache (NOVA-SEMVER-23-24) ([810b12c](https://github.com/ahincho/nova-java-spring-boot-gradle-plugin/commit/810b12c948946584cbf4eef417aaf54b836a417a))
* initial commit - Plugin Gradle para aplicar el meta-framework ([872edee](https://github.com/ahincho/nova-java-spring-boot-gradle-plugin/commit/872edee2537e2d2700387b6b11167f09c5729bc9))


### Bug Fixes

* **ci:** inline publish-on-tag and remove dirty closure for Gradle 9.6.1 ([d5ffdca](https://github.com/ahincho/nova-java-spring-boot-gradle-plugin/commit/d5ffdca605b95c10d61f8b9f3eb153b7559dfebb))
* **ci:** use PAT fallback for release-please to enable tag-triggered workflows ([a981140](https://github.com/ahincho/nova-java-spring-boot-gradle-plugin/commit/a9811405fd5a1dc756e28c05c8c2b9411ac4f2b9))
