/*
 * Copyright (C) 2013 tarent AG
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
 * CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 * TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

import org.openqa.selenium.htmlunit.HtmlUnitDriver

/*
 * Configuration script for Geb tests. Configuration values might be overwritten using system properties or may be
 * during runtime in Groovy code.
 * 
 * See http://www.gebish.org/manual/current/configuration.html for further details.
 */

/* put a directory to put reports in here (only filled by ReportingSpecs */
reportsDir = "target/geb-reports"
/* only report if test failed (not working for Spock yet) */
reportOnTestFailureOnly = false

/* clear cookies after each test */
autoClearCookies = true

/* Implicit waiting timings of any element */
waiting {
    timeout = 5
    retryInterval = 0.5
}

/* use this driver for testing */
//driver = { new FirefoxDriver() }
driver = { new HtmlUnitDriver(enableJavascript: false) }
