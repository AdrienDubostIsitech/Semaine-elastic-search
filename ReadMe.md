## SEMAINE ELASTICSEARCH

- Etapes pour installer localement Elastic Search: 
    - On récupère l'image Docker de la dernière version stable de ElasticSearch : `docker pull docker.elastic.co/elasticsearch/elasticsearch:8.7.1`
    - On crée un nouveau Docker network avec : `docker network create elastic`
    - On copie le dossier de certificat de securité du container docker sur la machine (en local) avec : `docker cp es01:/usr/share/elasticsearch/config/certs/http_ca.crt .`
    - On run le container Docker avec : `docker run --name es01 --net elastic -p 9200:9200 -it docker.elastic.co/elasticsearch/elasticsearch:8.7.1`. En plus de run notre container, cette commande génère un mot de passe et un token.
    - On fait ensuite un requête http pour se connecter à notre cluster ElasticSearch avec : `curl --cacert http_ca.crt -u elastic https://localhost:9200`. On donne ensuite le mot de passe générer par la commande `docker run` à l'étape précédente. 
    Si la connexion ne se fait pas on reçoit une erreur, sinon, si on se connecte au cluster on reçoit une réponse json tel que celle-ci : 
    `{
  "name" : "ab0f6a460419",
  "cluster_name" : "docker-cluster",
  "cluster_uuid" : "a9LMlVd_R1CnczQMmmkcyg",
  "version" : {
    "number" : "8.7.1",
    "build_flavor" : "default",
    "build_type" : "docker",
    "build_hash" : "f229ed3f893a515d590d0f39b05f68913e2d9b53",
    "build_date" : "2023-04-27T04:33:42.127815583Z",
    "build_snapshot" : false,
    "lucene_version" : "9.5.0",
    "minimum_wire_compatibility_version" : "7.17.0",
    "minimum_index_compatibility_version" : "7.0.0"
  },
  "tagline" : "You Know, for Search"
}`

Après avoir installé ElasticSearch, on peut maintenant le lançer sur notre machine : 
    On lance un container ElasticSearch avec cette commande : `docker run --name elasticsearch --net elastic -p 9200:9200 -p 9300:9300 -e "discovery.type=single-node" -t docker.elastic.co/elasticsearch/elasticsearch:8.7.1`. Avec cette commande on récupère en sortie le token pour pour lier Kibana à ElasticSearch et le mot de passe pour s'y connecter. Le username par défaut "elastic" est aussi crée. 

    On récupère ensuite l'image docker de kibana : `docker pull docker.elastic.co/kibana/kibana:8.7.1`

    Et on lance le container : `docker run --name kibana --net elastic -p 5601:5601 docker.elastic.co/kibana/kibana:8.7.1`

    Sur https://localhost:5601 on renseigne le token pour kibana et on peut ensuite se connecter avec le username : "elastic" et le mot de passe généré par l'execution du container ElasticSearch. 

    Nous pouvons ensuite visualiser les données indexée dans ElasticSearch !
