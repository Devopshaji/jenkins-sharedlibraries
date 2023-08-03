def call (String dockerRegistry, String dockerImageTag, String kubernetesDeployment, String kubernetesContainer, String helmChartName) {
    sh 'helm upgrade --install $helmChartName helm/ --set image.repository="$dockerRegistry:$dockerImageTag" --set deployment.name=$kubernetesDeployment --set image.containerName=$kubernetesContainer '
}

