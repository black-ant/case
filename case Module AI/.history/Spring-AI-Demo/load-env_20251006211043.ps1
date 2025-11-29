# PowerShell 脚本：加载 .env 文件中的环境变量

$envFile = ".env"

if (Test-Path $envFile) {
    Write-Host "正在加载环境变量从 $envFile ..." -ForegroundColor Green
    
    Get-Content $envFile | ForEach-Object {
        $line = $_.Trim()
        
        # 跳过空行和注释
        if ($line -and !$line.StartsWith("#")) {
            if ($line -match '^([^=]+)=(.*)$') {
                $name = $matches[1].Trim()
                $value = $matches[2].Trim()
                
                # 移除值两端的引号
                $value = $value -replace '^["'']|["'']$', ''
                
                [Environment]::SetEnvironmentVariable($name, $value, 'Process')
                Write-Host "  ✓ $name" -ForegroundColor Cyan
            }
        }
    }
    
    Write-Host "`n环境变量加载完成！" -ForegroundColor Green
    Write-Host "现在可以运行: mvn spring-boot:run" -ForegroundColor Yellow
} else {
    Write-Host "错误：找不到 .env 文件" -ForegroundColor Red
    Write-Host "请先复制 .env.example 为 .env 并填入配置" -ForegroundColor Yellow
    Write-Host "  cp .env.example .env" -ForegroundColor Cyan
}
