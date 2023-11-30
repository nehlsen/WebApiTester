# WebApiTester

## It started with a rough Idea ...

* _WebApiTester_ makes HTTP requests
* A Request may be `GET`, `POST`, etc.
* A Request may access the previous request and response to either be skipped or run in a modified way
* Multiple Request (or **Tasks**) are Bundled in a **Plan**
* A **Plan** can retain cookies to e.g. keep a session or authentication active
* Requests can ignore or clear cookies
* A Request may (by itself) be skipped (e.g. if logged in an authentication step may skip itself)
* A Request can either SUCCEED or FAIL (in which case execution stops and the Plan will fail too)
* It exists a global "Parameters" Registry which can provide parameters and values and each Request can access and modify it

_See also [WebApiTester-Dashboard](https://github.com/nehlsen/WebApiTesterDashboard)_
