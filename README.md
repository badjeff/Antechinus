Antechinus
===========

Wrapping getString method on Android.

##Getting started

### Dependency

```groovy
allprojects {
    repositories {
        ...
        maven { url "https://jitpack.io" }
    }
}
dependencies {
    compile 'com.github.badjeff:Antechinus:1.0.0'
}
```

### Installation

Define your default font using `AntechinusConfig`, in your `Application` class in the `#onCreate()` method.

```java
@Override
public void onCreate() {
    super.onCreate();
    AntechinusConfig.initDefault(new AntechinusConfig.Builder()
            .listenerFactory(new AntechinusListenerFactory() {
                @Override
                public String getString(Context context, int id, String defaultString) throws Resources.NotFoundException {
                    String s = defaultString;
                    // TODO: custom your string here
                    return s;
                }
            })
            .build());
}
```

### Inject into Context

Wrap the `Activity` Context:

```java
@Override
protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(AntechinusContextWrapper.wrap(newBase));
}
```

_You're good to go!_


#FAQ

### Why no jar?

We needed to ship a custom ID with Antechinus to improve the Font Injection flow. This
unfortunately means that is has to be an `aar`. But you're using Gradle now anyway right?

#Colaborators

- [@chrisjenx](http://github.com/chrisjenx)
- [@mironov-nsk](https://github.com/mironov-nsk)
- [@Roman Zhilich](https://github.com/RomanZhilich)
- [@Smuldr](https://github.com/Smuldr)
- [@Codebutler](https://github.com/codebutler)
- [@loganj](https://github.com/loganj)
- [@dlew](https://github.com/dlew)

#Licence

    Copyright 2015 Jeff Leung
    
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
    
        http://www.apache.org/licenses/LICENSE-2.0
    
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
