# Syncmaru
Internal Java console application for syncing LiveChart.me card object to a composite data model (MAL, Hummingbird) and then uploaded to Firebase. 

Feel free to look over the code. 

(You might be interested in checking on how MAL matching is being done since the official API doesn't provide getting objects by ID plus the official search API is prone to timeouts even with legit terms).

## Dependencies
Below are the libraries used for Syncmaru:
- OkHttp
- Jsoup
- Apache Commons I/O
- json.org - JSON
- Google's GSON

## Building
Provided that you already have the dependencies, directly clone the repo via Git or just use IntelliJ VCS checkout and compile.


## License
Wherever unmarked, the applicable parts of the software follows the license below:

The MIT License (MIT)

Copyright (c) 2014 Nizlumina Studio (Malaysia)

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
