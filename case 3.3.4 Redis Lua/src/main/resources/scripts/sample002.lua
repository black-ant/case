---
--- Generated by EmmyLua(https://github.com/EmmyLua)
--- Created by 10169.
--- DateTime: 2021/8/13 13:48
---
---
--- Generated by EmmyLua(https://github.com/EmmyLua)
--- Created by 10169.
--- DateTime: 2021/8/13 13:42
---
if redis.call("get",KEYS[1]) == ARGV[1] then
    return redis.call("del",KEYS[1])
else
    return 0
end
