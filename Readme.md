# MatrixStats2

[![Build Status](https://github.com/TheMatrixNetwork/MatrixStats2/workflows/Build/badge.svg)](../../actions?query=workflow%3ABuild)
[![GitHub release (latest SemVer including pre-releases)](https://img.shields.io/github/v/release/TheMatrixNetwork/MatrixStats2?include_prereleases&label=release)](../../releases)

Minecraft plugin which provides player stats to an angular frontend. The stats can be accessed by players by logging in using their in-game credentials. (AuthMe)

### Commit message format

See the [conventional commit homepage](https://www.conventionalcommits.org/) for more details and examples on the topic. But here is a quick summary to get you started.

> The Conventional Commits specification is a lightweight convention on top of commit messages. It provides an easy set of rules for creating an explicit commit history; which makes it easier to write automated tools on top of. This convention dovetails with [SemVer](http://semver.org/), by describing the features, fixes, and breaking changes made in commit messages.

The commit message should be structured as follows:

```text
<type>[optional scope]: <description>

[optional body]

[optional footer(s)]
```

The commit contains the following structural elements, to communicate intent to the consumers of your library or plugin:

* `fix:` a commit of the type fix patches a bug in your codebase (this correlates with PATCH in semantic versioning).
* `feat:` a commit of the type feat introduces a new feature to the codebase (this correlates with MINOR in semantic versioning).
* `BREAKING CHANGE:` a commit that has a footer BREAKING CHANGE:, or appends a ! after the type/scope, introduces a breaking API change (correlating with MAJOR in semantic versioning). A BREAKING CHANGE can be part of commits of any type.
* types other than fix: and feat: are allowed, for example @commitlint/config-conventional (based on the the Angular convention) recommends `build:`, `chore:`, `ci:`, `docs:`, `style:`, `refactor:`, `perf:`, `test:`, and others.
* footers other than `BREAKING CHANGE: <description>` may be provided and follow a convention similar to git trailer format.
Additional types are not mandated by the Conventional Commits specification, and have no implicit effect in semantic versioning (unless they include a BREAKING CHANGE).

A scope may be provided to a commit’s type, to provide additional contextual information and is contained within parenthesis, e.g., `feat(parser): add ability to parse arrays`.

Here are some examples:

<details>
<summary>Commit message with description and breaking change footer</summary>

```text
feat: allow provided config object to extend other configs

BREAKING CHANGE: `extends` key in config file is now used for extending other config files
```

</details>

<details>
<summary>Commit message with no body</summary>

```text
docs: correct spelling of CHANGELOG
```

</details>

<details>
<summary>Commit message with scope</summary>

```text
feat(lang): add polish language
```

</details>

<details>
<summary>Commit message with multi-paragraph body and multiple footers</summary>

```text
fix: correct minor typos in code

see the issue for details

on typos fixed.

Reviewed-by: Z
Refs #133
```

</details>

## References

* [Spigradle](https://github.com/spigradle/spigradle/): *provides awesome gradle tasks that make your live a lot easier*
* [semantic-release](https://semantic-release.gitbook.io/semantic-release/): *in my opinion every project should use this!*
* [conventional commit messages](https://www.conventionalcommits.org/): *do commit message the right way*
