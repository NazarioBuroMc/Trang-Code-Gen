                        
                        #################################
                        ###### TRANG-CODE-GEN-BURO ######
                        #################################

Este proyecto se debe de generar un JAR manualmente llamado "trang-code-gen-buro.jar"
    Para ser instalado en nuestro repositorio de MAVEN
        mvn install:install-file -Dfile=trang-code-gen-buro.jar -DgroupId=mx.com.codegen -DartifactId=trang-code-gen -Dversion=1.0 -Dpackaging=jar
	Para extraer la dependencia sera
	    <dependency>
            <groupId>mx.com.codegen</groupId>
            <artifactId>trang-code-gen</artifactId>
            <version>1.0</version>
        </dependency>