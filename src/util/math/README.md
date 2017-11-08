# Java Math Utilities


## Disclaimer
Originally developed by Ramil Shaymardanov in 2017 for Duke CS308

Inspired by NumPy, SymPy and Theano

Refer to ./docs/index.html for documentation

> P.S. I do realise that for the first assignment that looks
ridiculously over-engineered and under-implemented, however, I plan to make a use
of it in future assignments, as well as my other future
Java endeavours, adding functionality as I go

## NOTE
Basic structure is:

```
math
    num
        [Numeric Classes]
    sym
        [Symbolc Classes]
```

Therefore, in order to import numeric Vector you do:
```java
import math.num.Vector;
```
and symbolic:
```java
import math.sym.Vector;
```
This structure, however, may create problem if you want to
import both simultaneously. In order to solve this, do:

```java
class VectorNum extends math.num.Vector {}
class VectorSym extends math.sym.Vector {}
```

This structure is based on assumption that only numeric
or symbolic class will normally be used in scope of one
class, however, it may be a subject to change.



