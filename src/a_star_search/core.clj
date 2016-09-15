(ns a-star-search.core
  (:require [search.algorithms :as alg]
            [problems.n-puzzle :as np]
            [problems.maze :as mz]
            [clojure.tools.namespace.repl :refer [refresh]])
  (:gen-class))

(def x "x") ;Pay no attention to the x behind the curtain. This varibale lets us use x without quotes.
(def test-board [[0 0 0 0 0 0 0 0]
                 [x x x x 0 x x 0]
                 [0 x x x 0 x 0 0]
                 [0 0 0 0 0 x 0 x]
                 [0 x 0 x 0 0 0 x]])

(defn print-results [result path costs goal-state]
  (println (str "We explored " (count result) " states."))
  (println "The path, in [row col] format, to the solution is:")
  (doseq [b (map :current-pos path)]
    (println b))
  (when costs
    (println "The path has" (count path) "steps and its cost is" (costs goal-state))))

(defn -main
  "Search for a hard-coded N-puzzle target state."
  [& args]
  (let [start-state (mz/->State test-board [0 0])
        goal-state (mz/->State test-board [2 7])
        max-states 1000000
        costs (constantly 1)
        came-from (alg/breadth-first-search mz/children max-states start-state goal-state)
        ; [came-from costs] (alg/shortest-path np/children (constantly 1)
        ;                                      max-states start-state goal-state )
        ; [came-from costs] (alg/shortest-path np/children np/prefer-horizontal-cost
        ;                                      max-states start-state goal-state)
        path (alg/extract-path came-from start-state goal-state)]
    (print-results came-from path costs goal-state)))

; Run (with timing) with
; (time (-main))
; or
; time lein run
