
##Make a docker container for Postgres DB

-> docker run --name storage -e POSTGRES_USER=xpress -e POSTGRES_PASSWORD=p@ssword -e POSTGRES_DB=storage -p 5432:5432 -d postgres
