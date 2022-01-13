#!/usr/bin/env zsh

while true
do
  git fetch;
  changes=$(git log HEAD..origin/trunk);
  if [ -z "$changes" ]
  then
    echo "No changes detected";
    sleep 30;
  else
    git show HEAD..origin/trunk && git pull --rebase;
  fi
done
