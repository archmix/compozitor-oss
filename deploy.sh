mvn -DskipTests deploy

git clone git@github.com:mrbraztech/skynet.git

cp -Ru compozitor-engine/maven-repo ./skynet
cp -Ru compozitor-generator/maven-repo ./skynet
cp -Ru compozitor-processor/maven-repo ./skynet
cp -Ru compozitor-template/maven-repo ./skynet
cp -Ru maven-repo ./skynet

cd skynet
git config user.email "braz@mrbraz.tech"
git config user.name "Braz"

git add .
git commit -m "Deploy artifact to github"
git push origin gh-pages