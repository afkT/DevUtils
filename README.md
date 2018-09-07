# About

> DevUtils 是一个 Android 工具库, 主要根据不同功能模块，封装快捷使用的工具类及 API 方法调用。
> <p>该项目尽可能的便于开发人员，快捷、快速开发安全可靠的项目，以及内置部分常用的资源文件，如color.xml、(toast) layout.xml等

# Gradle

Step 1. Add the JitPack repository to your build file
```
allprojects {
	repositories {
		maven { url 'https://jitpack.io' }
	}
}
```

Step 2. Add the dependency
```
dependencies {
	// 因为内含 res 文件, 使用 aar 方式调用
	implementation 'com.github.afkT:DevUtils:1.0.0@aar'
	// implementation 'com.github.afkT:DevUtils:latest.release@aar'
}
```

## Documentation

- [README - API](https://github.com/afkT/DevUtils/blob/master/DevLibUtils/README.md)

# Thanks

> 感谢以下开源项目的作者，本项目中有些功能受你们项目灵感的启发，有些功能也用到你们的代码完成。

- [orhanobut/logger](https://github.com/orhanobut/logger)
- [laobie/StatusBarUtil](https://github.com/laobie/StatusBarUtil)
- [GrenderG/Toasty](https://github.com/GrenderG/Toasty)
- [Blankj/AndroidUtilCode](https://github.com/Blankj/AndroidUtilCode)
- [l123456789jy/Lazy](https://github.com/l123456789jy/Lazy)
- [yangfuhai/ASimpleCache](https://github.com/yangfuhai/ASimpleCache)
- [AbrahamCaiJin/CommonUtilLibrary](https://github.com/AbrahamCaiJin/CommonUtilLibrary)
- [litesuits/android-common](https://github.com/litesuits/android-common)