(defproject n-puzzle-as-search "0.1.0-SNAPSHOT"
  :description "Demo of solving N-puzzle problems via simple search"
  :url "https://github.com/NicMcPhee/n-puzzle-as-search"
  :license {:name "MIT License"
            :url "https://opensource.org/licenses/MIT"}
  :dependencies [[org.clojure/clojure "1.8.0"]]
  :main ^:skip-aot n-puzzle-as-search.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}
             :dev {:dependencies [[midje "1.8.3"]]}})
