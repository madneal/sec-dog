# sec-dog

![Build](https://github.com/madneal/sec-dog/workflows/Build/badge.svg)
[![Version](https://img.shields.io/jetbrains/plugin/v/19098-sca-checker.svg)](https://plugins.jetbrains.com/plugin/19098-sca-checker)
[![Downloads](https://img.shields.io/jetbrains/plugin/d/19098-sca-checker.svg)](https://plugins.jetbrains.com/plugin/19098-sca-checker)

<!-- Plugin description -->
This is the first goland plugin for SCA of Go. It focuses on the dependency security of the Go project. It will generate the SCA report for the dependencies with vulnerabilities.
<!-- Plugin description end -->

## Usage

### By menu

![image](https://user-images.githubusercontent.com/12164075/166859685-3ced9cbf-6260-4c4f-8653-8457f6d035b4.png)

### By Action

1. Press `Cmd+Shit+A`
2. Search `SCA Check`

![image](https://user-images.githubusercontent.com/12164075/166859772-079e6b39-d767-41c0-9805-98863c61ffda.png)

## Installation

- Using IDE built-in plugin system:
  
  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>Marketplace</kbd> > <kbd>Search for "sec-dog"</kbd> >
  <kbd>Install Plugin</kbd>
  
- Manually:

  Download the [latest release](https://github.com/madneal/sec-dog/releases/latest) and install it manually using
  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>⚙️</kbd> > <kbd>Install plugin from disk...</kbd>


---
Plugin based on the [IntelliJ Platform Plugin Template][template].

[template]: https://github.com/JetBrains/intellij-platform-plugin-template
