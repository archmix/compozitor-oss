mvn -DskipTests deploy

git clone git@github.com:mrbraztech/skynet.git

for directory in $(find ./ -type d -name "maven-repo");
do
    echo $directory
    cp -r $directory ./skynet
    rm -rf $directory
done 

cd skynet
git config user.email "braz@mrbraz.tech"
git config user.name "Braz"

git add .
git commit -m "Deploy artifact to github"
git push origin gh-pages