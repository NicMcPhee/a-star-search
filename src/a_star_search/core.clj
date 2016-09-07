(ns a-star-search.core
  (:require clojure.set
            [a-star-search.state :as s]
            [clojure.data.priority-map :as pm])
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
      (let [current (peek frontier)
            children (set (s/children current))
            unvisited-children (clojure.set/difference children visited)
            new-frontier (reduce conj (pop frontier) unvisited-children)
            new-visited (clojure.set/union children visited)
            new-came-from (reduce #(assoc %1 %2 current) came-from unvisited-children)]
        (recur (- max-states (count unvisited-children))
               new-frontier
               new-visited
               new-came-from)))))

(defn shortest-path [max-states start-state goal-state cost-fn]
  (loop [max-states max-states
         frontier (pm/priority-map start-state 0)
         came-from {}
         cost-so-far {start-state 0}]
    (if (or (neg? max-states)
            (empty? frontier)
            (= (first (peek frontier)) goal-state))
      [came-from cost-so-far]
      (let [current (first (peek frontier))
            current-cost (cost-so-far current)
            children (set (s/children current))
            children-costs (reduce #(assoc %1 %2 (+ current-cost (cost-fn current %2))) {} children)
            children-to-add (filter #(or (not (contains? cost-so-far %))
                                         (< (children-costs %) (cost-so-far %))) children)
            new-cost-so-far (reduce #(assoc %1 %2 (children-costs %2)) cost-so-far children-to-add)
            new-frontier (reduce #(assoc %1 %2 (children-costs %2)) (pop frontier) children-to-add)
            new-came-from (reduce #(assoc %1 %2 current) came-from children-to-add)]
        (recur (- max-states (count children-to-add))
               new-frontier
               new-came-from
               new-cost-so-far)))))

(defn extract-path [came-from start-state goal-state]
  (loop [current-state goal-state
         path []]
    (cond
      (nil? current-state) nil
      (= current-state start-state) (reverse (conj path start-state))
      :else (recur (came-from current-state)
                   (conj path current-state)))))

(defn prefer-horizontal-cost [s t]
  (let [s-x (first (:blank-position s))
        t-x (first (:blank-position t))]
    (inc (Math/abs (- s-x t-x)))))

(defn -main
  "Search for a hard-coded target state."
  [& args]
  (let [start-state (s/->State [[0 1 3] [4 2 5] [7 8 6]] [0 0])
        goal-state (s/->State [[0 1 2] [3 4 5] [6 7 8]] [0 0])
        max-states 1000000
        costs nil
        ; result (breadth-first-search max-states start-state goal-state)
        [result costs] (shortest-path max-states start-state goal-state (constantly 1))
        ; [result costs] (shortest-path max-states start-state goal-state prefer-horizontal-cost)
        path (extract-path result start-state goal-state)
        ]
    (println (str "We explored " (count result) " states."))
    (println "The path to the solution is:")
    (doseq [b (map :board path)]
      (println b))
    (when costs
      (println "The path has" (count path) "steps and its cost is" (costs goal-state)))
    ))

; (time (-main))
