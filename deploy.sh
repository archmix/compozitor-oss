mvn -DskipTests deploy

git clone git@github.com:mrbraztech/skynet.git

cp -r compozitor-engine/maven-repo ./skynet
cp -r compozitor-generator/maven-repo ./skynet
cp -r compozitor-processor/maven-repo ./skynet
cp -r compozitor-template/maven-repo ./skynet

cd skynet
git config user.email "braz@mrbraz.tech"
git config user.name "Braz"

git add .
git commit -m "Deploy artifact to github"
git push origin gh-pages