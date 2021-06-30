local test = redis.call("get", "test")
local time = redis.call("get", "time")
redis.call("setex", "test", 10, "xx")
redis.call("setex", "time", 10, "xx")
return { test, time }
