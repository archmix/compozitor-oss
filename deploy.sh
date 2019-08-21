mvn -DskipTests deploy

git clone git@github.com:mrbraztech/skynet.git
git checkout -b gh-pages

cp -r compozitor-engine/maven-repo/ ./skynet
rm -rf compozitor-engine/maven-repo

cp -r compozitor-generator/maven-repo/ ./skynet
rm -rf compozitor-generator/maven-repo

cp -r compozitor-processor/maven-repo/ ./skynet
rm -rf compozitor-processor/maven-repo

cp -r compozitor-template/maven-repo/ ./skynet
rm -rf compozitor-template/maven-repo

cd skynet
git config user.email "braz@mrbraz.tech"
git config user.name "Braz"

git add .
git commit -m "Deploy artifact to github"
git push origin gh-pages