mvn -DskipTests deploy

git clone git@github.com:mrbraztech/skynet.git
git checkout -b gh-pages

for d in **/*compozitor*/; do 
  if [ -d "$d" ]; then
    echo $d;
    mavenRepo = "$d/maven-repo"
    cp -r $mavenRepo ./skynet
    rm -rf $mavenRepo
done

cd skynet
git config user.email "braz@mrbraz.tech"
git config user.name "Braz"

git add .
git commit -m "Deploy artifact to github"
git push origin gh-pages