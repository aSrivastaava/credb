param(
    [string]$WikiRepoUrl = "https://github.com/aSrivastaava/credb.wiki.git",
    [string]$WorkDir = ".wiki-sync",
    [string]$StatusFile = "project-status.json",
    [switch]$Push
)

Set-StrictMode -Version Latest
$ErrorActionPreference = "Stop"

$repoRoot = (Get-Location).Path
$wikiPath = Join-Path $repoRoot $WorkDir
$wikiRoadmapFile = Join-Path $wikiPath "Roadmap.md"
$updateScript = Join-Path $repoRoot "scripts\update-roadmap.ps1"

if (-not (Test-Path -LiteralPath $StatusFile)) {
    throw "Status file not found: $StatusFile"
}

if (-not (Test-Path -LiteralPath $updateScript)) {
    throw "Update script not found: $updateScript"
}

if (-not (Test-Path -LiteralPath $wikiPath)) {
    git clone $WikiRepoUrl $WorkDir | Out-Null
}

git -C $wikiPath pull --ff-only origin master | Out-Null

powershell -ExecutionPolicy Bypass -File $updateScript -StatusFile $StatusFile -WikiRoadmapFile $wikiRoadmapFile

$statusOutput = git -C $wikiPath status --short -- Roadmap.md

if ([string]::IsNullOrWhiteSpace($statusOutput)) {
    Write-Host "Wiki roadmap is already up to date."
    exit 0
}

git -C $wikiPath add Roadmap.md
git -C $wikiPath commit -m "Update wiki roadmap"

if ($Push) {
    git -C $wikiPath push origin master
}
else {
    Write-Host "Wiki roadmap committed locally. Re-run with -Push to publish."
}
