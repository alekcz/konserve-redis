(defproject alekcz/konserve-redis "0.1.0-SNAPSHOT"
  :description "A redis backend for konserve using carmine."
  :url "https://github.com/alekcz/konserve-redis"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :aot :all
  :dependencies [[org.clojure/clojure "1.10.2-alpha1"]
                 [com.taoensso/carmine "2.21.0-RC1"]
                 [io.replikativ/konserve "0.6.0-alpha1"]]
  :repl-options {:init-ns konserve-redis.core}
  :plugins [[lein-cloverage "1.2.0"]]
  :profiles { :dev {:dependencies [[metosin/malli "0.0.1-20200404.091302-14"]]}})
