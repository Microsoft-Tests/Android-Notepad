#!/usr/bin/env bash 
wget https://dot.net/v1/dotnet-install.sh 
chmod +x dotnet-install.sh 
./dotnet-install.sh --version 6.0.3 --install-dir "$AGENT_TOOLSDIRECTORY/dotnet"