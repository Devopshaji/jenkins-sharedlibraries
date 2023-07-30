def call(String dockerRegistry, String dockerImageTag, String awsCredID, String awsRegion){
    
    if ! command -v aws &> /dev/null; then
        echo "AWS CLI not found. Installing AWS CLI..."
        curl "https://awscli.amazonaws.com/awscli-exe-linux-x86_64.zip" -o "awscliv2.zip" &> /dev/null
        unzip awscliv2.zip &> /dev/null
        sudo ./aws/install &> /dev/null
        rm -rf awscliv2.zip aws
        echo "AWS CLI installed successfully."
    fi
    
    withCredentials([usernamePassword(
        credentialsId: "$awsCredID",
        usernameVariable: "awsAccessKey",
        passwordVariable: "awsSecretKey"
    )]) {
        sh "aws configure set aws_access_key_id $awsAccessKey"
        sh "aws configure set aws_secret_access_key $awsSecretKey"
        sh "aws configure set region $awsRegion"

        sh "aws ecr get-login-password --region $awsRegion | docker login --username AWS --password-stdin $dockerRegistry"
    }

    sh "docker image push $dockerRegistry:$dockerImageTag"
    sh "docker image push $dockerRegistry:latest"   
}