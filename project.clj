(defproject n-puzzle-as-search "0.1.0-SNAPSHOT"
  :description "Demonstration of how we can use algorithms like A* to solve simple problems like the N-puzzle"
  :url "https://github.com/NicMcPhee/a-star-search"
  :license {:name "MIT License"
            :url "https://opensource.org/licenses/MIT"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/data.priority-map "0.0.7"]]
  :main ^:skip-aot a-star-search.pegCore
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}
             :dev {:dependencies [[midje "1.8.3"]]}}
  :pluins [[lein-midje "3.1.3"]]
  :jvm-opts ["-Xms1G" "-Xmx1G"])
