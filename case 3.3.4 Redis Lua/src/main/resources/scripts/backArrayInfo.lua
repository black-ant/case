local i = tonumber(ARGV[1])
local res
while (i > 0) do
    res = redis.call('lpush', KEYS[1], math.random())
    i = i - 1
end
return res
