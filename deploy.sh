git config user.email "braz@mrbraz.tech"
git config user.name "Braz"

git checkout -b gh-pages
git pull origin gh-pages
git merge master

mvn -DskipTests deploy

cp -r compozitor-engine/maven-repo/ ./
rm -rf compozitor-engine/maven-repo

cp -r compozitor-generator/maven-repo/ ./
rm -rf compozitor-generator/maven-repo

cp -r compozitor-processor/maven-repo/ ./
rm -rf compozitor-processor/maven-repo

cp -r compozitor-template/maven-repo/ ./
rm -rf compozitor-template/maven-repo

