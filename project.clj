(defproject cloneit "1.0.0"
  :description      "Image to Ascii-art converter"
  :url              "http://www.bestinclass.dk"
  :library-path     "lib/"
  :namespaces       [claskii]
  :main             claskii
  :dependencies     [[org.clojure/clojure "1.6.0"]
		     [compojure "1.2.0"]
                     [hiccup "1.0.5"]]
  :dev-dependencies [[swank-clojure "1.4.3"]])
