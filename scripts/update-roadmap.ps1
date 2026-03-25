param(
    [string]$StatusFile = "project-status.json",
    [string]$RoadmapFile = "docs/ROADMAP.md",
    [string]$WikiRoadmapFile = ""
)

Set-StrictMode -Version Latest
$ErrorActionPreference = "Stop"

if (-not (Test-Path -LiteralPath $StatusFile)) {
    throw "Status file not found: $StatusFile"
}

$status = Get-Content -LiteralPath $StatusFile -Raw | ConvertFrom-Json

function New-RoadmapContent {
    param(
        [pscustomobject]$RoadmapStatus,
        [bool]$ForWiki
    )

    $lines = New-Object System.Collections.Generic.List[string]
    $lines.Add("# Roadmap")
    $lines.Add("")

    if ($ForWiki) {
        $lines.Add("This page is generated from the repository `project-status.json` file.")
    }
    else {
        $lines.Add("This file is generated from `project-status.json` using `scripts/update-roadmap.ps1`.")
    }

    $lines.Add("")
    $lines.Add("## Summary")
    $lines.Add("")

    foreach ($phase in $RoadmapStatus.phases) {
        $lines.Add("- ``$($phase.name)``: $($phase.status)")
    }

    foreach ($phase in $RoadmapStatus.phases) {
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

    return ($lines -join [Environment]::NewLine) + [Environment]::NewLine
}

$content = New-RoadmapContent -RoadmapStatus $status -ForWiki $false
Set-Content -LiteralPath $RoadmapFile -Value $content -Encoding UTF8

Write-Host "Updated $RoadmapFile from $StatusFile"

if ($WikiRoadmapFile -ne "") {
    $wikiDirectory = Split-Path -Parent $WikiRoadmapFile

    if ($wikiDirectory -and -not (Test-Path -LiteralPath $wikiDirectory)) {
        throw "Wiki directory does not exist: $wikiDirectory"
    }

    $wikiContent = New-RoadmapContent -RoadmapStatus $status -ForWiki $true
    Set-Content -LiteralPath $WikiRoadmapFile -Value $wikiContent -Encoding UTF8
    Write-Host "Updated $WikiRoadmapFile from $StatusFile"
}
