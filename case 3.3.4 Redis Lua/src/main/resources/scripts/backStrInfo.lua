-- 1 通过 redis.call 调用 redis 操作 ,  get 为操作类型 (和 Redis 语法一致)
-- 2 KEYS[1] 获取第一个 key 值 , ARGV[1] 获取第一个 Value 值
-- 3 通过 .. 进行参数的连接
local firstStr = redis.call('get', KEYS[1])
local secondStr = redis.call('get', 'secondStr')
local lastStr = ARGV[1]

print("----------")

return firstStr .. '-' .. secondStr .. '-' .. lastStr
