Set-StrictMode -Version Latest
$ErrorActionPreference = "Stop"

git config core.hooksPath .githooks

Write-Host "Configured Git hooks path to .githooks"
Write-Host "The pre-commit hook will now regenerate docs/ROADMAP.md before each commit."
