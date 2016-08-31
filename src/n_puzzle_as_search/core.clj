(ns n-puzzle-as-search.core
  (:require clojure.set
            [n-puzzle-as-search.state :as s])
  (:gen-class))

(defn breadth-first-search [max-states start-state goal-state]
  (loop [max-states max-states
         frontier (conj (clojure.lang.PersistentQueue/EMPTY) start-state)
         visited #{}
         came-from {}]
    (if (or (neg? max-states)
            (empty? frontier)
            (= (peek frontier) goal-state))
      came-from
      (when-not (empty? frontier)
        (let [current (peek frontier)
              children (set (s/children current))
              unvisited-children (clojure.set/difference children visited)
              new-frontier (reduce conj (pop frontier) unvisited-children)
              new-visited (clojure.set/union children visited)]
          (recur (- max-states (count unvisited-children))
                 new-frontier
                 new-visited
                 (reduce #(assoc %1 %2 current) came-from unvisited-children)))))))

(defn extract-path [came-from start-state goal-state]
  (loop [current-state goal-state
         path []]
    (cond
      (nil? current-state) nil
      (= current-state start-state) (reverse (conj path start-state))
      :else (recur (came-from current-state)
                   (conj path current-state)))))

(defn -main
  "Search for a hard-coded target state."
  [& args]
  (let [start-state (s/->State [[0 1 3] [4 2 5] [7 8 6]] [0 0])
        goal-state (s/->State [[0 1 2] [3 4 5] [6 7 8]] [0 0])
        max-states 1000000
        result (breadth-first-search max-states start-state goal-state)]
    (println (str "We explored " (count result) " states."))
    (println "The path to the solution is:")
    (doseq [b (map :board (extract-path result start-state goal-state))]
      (println b))
    ))
