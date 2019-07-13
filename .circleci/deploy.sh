git config user.email "braz@mrbraz.tech"
git config user.name "Braz"

git checkout -b maven-repo
git pull origin maven-repo
git merge master

mvn -DskipTests deploy

cp -r compozitor-engine/maven-repo/ ./
rm -rf compozitor-engine/maven-repo

cp -r compozitor-generator/maven-repo/ ./
rm -rf compozitor-generator/maven-repo

cp -r compozitor-template/maven-repo/ ./
rm -rf compozitor-template/maven-repo

git add .
git commit -m "Deploy artifact to github"
git push origin maven-repo

git checkout master
