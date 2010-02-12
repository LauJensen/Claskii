(defproject cloneit "1.0.0"
  :description      "Image to Ascii-art converter"
  :url              "http://www.bestinclass.dk"
  :library-path     "lib/"
  :namespaces       [claskii]
  :main             claskii
  :dependencies     [[org.clojure/clojure "1.1.0-new-SNAPSHOT"]
		     [org.clojure/clojure-contrib "1.1.0-new-SNAPSHOT"]
		     [compojure "0.3.2"]]
  :dev-dependencies [[swank-clojure "1.1.0"]])