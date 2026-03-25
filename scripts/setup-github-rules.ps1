param(
    [string]$Owner = "aSrivastaava",
    [string]$Repo = "credb",
    [string]$Branch = "main",
    [switch]$SkipLabels,
    [switch]$SkipBranchProtection
)

Set-StrictMode -Version Latest
$ErrorActionPreference = "Stop"

function Require-Command {
    param([string]$Name)

    if (-not (Get-Command $Name -ErrorAction SilentlyContinue)) {
        throw "Required command '$Name' was not found."
    }
}

function Invoke-GhJson {
    param(
        [string]$Method,
        [string]$Path,
        [string]$BodyJson
    )

    $tempFile = [System.IO.Path]::GetTempFileName()
    try {
        Set-Content -LiteralPath $tempFile -Value $BodyJson -Encoding UTF8
        gh api `
            --method $Method `
            -H "Accept: application/vnd.github+json" `
            $Path `
            --input $tempFile | Out-Null
    }
    finally {
        if (Test-Path $tempFile) {
            Remove-Item -LiteralPath $tempFile -Force
        }
    }
}

Require-Command "gh"

Write-Host "Checking GitHub authentication..."
gh auth status

if (-not $SkipLabels) {
    Write-Host "Applying labels..."

    $labels = @(
        @{ name = "docs"; color = "0075ca"; description = "Documentation changes" },
        @{ name = "backend"; color = "1d76db"; description = "Backend work" },
        @{ name = "crawler"; color = "0e8a16"; description = "Crawler agent work" },
        @{ name = "frontend"; color = "fbca04"; description = "Frontend work" },
        @{ name = "ai"; color = "5319e7"; description = "AI and LM Studio work" },
        @{ name = "database"; color = "006b75"; description = "Database and schema work" },
        @{ name = "architecture"; color = "d876e3"; description = "Architecture discussions and changes" },
        @{ name = "bug"; color = "d73a4a"; description = "Bug reports and fixes" },
        @{ name = "enhancement"; color = "a2eeef"; description = "Feature requests and improvements" },
        @{ name = "phase-1"; color = "bfdadc"; description = "Phase 1 foundation work" },
        @{ name = "phase-2"; color = "bfd4f2"; description = "Phase 2 crawler MVP work" },
        @{ name = "phase-3"; color = "c5def5"; description = "Phase 3 indexing work" },
        @{ name = "phase-4"; color = "d4c5f9"; description = "Phase 4 API work" },
        @{ name = "phase-5"; color = "f9d0c4"; description = "Phase 5 frontend work" },
        @{ name = "phase-6"; color = "fef2c0"; description = "Phase 6 AI integration work" },
        @{ name = "phase-7"; color = "c2e0c6"; description = "Phase 7 semantic search work" },
        @{ name = "phase-8"; color = "f9c2e0"; description = "Phase 8 hardening work" }
    )

    foreach ($label in $labels) {
        $path = "repos/$Owner/$Repo/labels/$($label.name)"
        $body = ($label | ConvertTo-Json -Compress)
        Invoke-GhJson -Method "PUT" -Path $path -BodyJson $body
        Write-Host "Applied label: $($label.name)"
    }
}

if (-not $SkipBranchProtection) {
    Write-Host "Applying branch protection to '$Branch'..."

    $protection = @{
        required_status_checks = $null
        enforce_admins = $false
        required_pull_request_reviews = @{
            dismiss_stale_reviews = $true
            require_code_owner_reviews = $false
            required_approving_review_count = 1
            require_last_push_approval = $false
        }
        restrictions = $null
        required_linear_history = $false
        allow_force_pushes = @{
            enabled = $false
        }
        allow_deletions = @{
            enabled = $false
        }
        block_creations = $false
        required_conversation_resolution = $true
        lock_branch = $false
        allow_fork_syncing = $false
    } | ConvertTo-Json -Depth 10

    Invoke-GhJson `
        -Method "PUT" `
        -Path "repos/$Owner/$Repo/branches/$Branch/protection" `
        -BodyJson $protection

    Write-Host "Branch protection applied to '$Branch'."
}

Write-Host "GitHub repository rules setup completed."
