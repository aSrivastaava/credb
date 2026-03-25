param(
    [string]$StatusFile = "project-status.json",
    [string]$RoadmapFile = "docs/ROADMAP.md"
)

Set-StrictMode -Version Latest
$ErrorActionPreference = "Stop"

if (-not (Test-Path -LiteralPath $StatusFile)) {
    throw "Status file not found: $StatusFile"
}

$status = Get-Content -LiteralPath $StatusFile -Raw | ConvertFrom-Json

$lines = New-Object System.Collections.Generic.List[string]
$lines.Add("# Roadmap")
$lines.Add("")
$lines.Add("This file is generated from `project-status.json` using `scripts/update-roadmap.ps1`.")
$lines.Add("")
$lines.Add("## Summary")
$lines.Add("")

foreach ($phase in $status.phases) {
    $lines.Add("- ``$($phase.name)``: $($phase.status)")
}

foreach ($phase in $status.phases) {
    $lines.Add("")
    $lines.Add("## $($phase.name)")
    $lines.Add("")
    $lines.Add("Status: ``$($phase.status)``")
    $lines.Add("")

    foreach ($task in $phase.tasks) {
        $checkbox = if ($task.done) { "[x]" } else { "[ ]" }
        $lines.Add("- $checkbox $($task.text)")
    }
}

$content = ($lines -join [Environment]::NewLine) + [Environment]::NewLine
Set-Content -LiteralPath $RoadmapFile -Value $content -Encoding UTF8

Write-Host "Updated $RoadmapFile from $StatusFile"
