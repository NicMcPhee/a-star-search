(ns search.algorithms
  (:require clojure.set
            [clojure.data.priority-map :as pm])
  (:gen-class))

(defn make-tree [gen-children root-state]
  (let [children (map (partial make-tree gen-children) (gen-children root-state))]
    {:state root-state
     :children children}))

(defn sdf [tree]
  (lazy-seq (cons (:state tree) (apply concat (map sdf (:children tree))))))

(defn do-sbf [nodes]
  (if-let [[next & the-rest] nodes]
    (lazy-seq (cons (:state next) (do-sbf (concat the-rest (:children next)))))
    []))

(defn sbf [tree]
  (do-sbf [tree]))

(defn do-breadth-flatten
  [children-fn unexplored-states]
  (if (empty? unexplored-states)
    []
    (if-let [[next & the-rest] unexplored-states]
      (lazy-seq (cons next (do-breadth-flatten children-fn (lazy-cat the-rest (children-fn next)))))
      [])))

(defn do-breadth-flatten-reduce
  [children-fn unexplored-states]
  (reduce
    (fn [rr s] (lazy-seq (cons s (lazy-cat rr (children-fn s)))))
    []
    unexplored-states))

(defn breadth-flatten
  [children-fn start-state]
  (do-breadth-flatten children-fn [start-state]))

(defn do-depth-flatten
  [children-fn unexplored-states]
  (if (empty? unexplored-states)
    []
    (if-let [[next & the-rest] unexplored-states]
      (lazy-seq (cons next (do-depth-flatten children-fn (lazy-cat (children-fn next) the-rest))))
      [])))

(defn depth-flatten
  [children-fn start-state]
  (do-depth-flatten children-fn [start-state]))

(defn breadth-first-search [children-fn max-states start-state goal-state]
  (loop [max-states max-states
         frontier (conj (clojure.lang.PersistentQueue/EMPTY) start-state)
         visited #{}
         came-from {}]
    (if (or (neg? max-states)
            (empty? frontier)
            (= (peek frontier) goal-state))
      came-from
      (let [current (peek frontier)
            children (set (children-fn current))
            unvisited-children (clojure.set/difference children visited)
            new-frontier (reduce conj (pop frontier) unvisited-children)
            new-visited (clojure.set/union children visited)
            new-came-from (reduce #(assoc %1 %2 current) came-from unvisited-children)]
        (recur (- max-states (count unvisited-children))
               new-frontier
               new-visited
               new-came-from)))))

(defn shortest-path [children-fn cost-fn max-states start-state goal-state]
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
            children (set (children-fn current))
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

(defn heuristic-search [children-fn heuristic-fn start-state goal-state & {:keys [max-states] :or {max-states 1000000}}]
  (loop [frontier (pm/priority-map start-state 0)
         came-from {}
         visited #{}]
    (if (or (empty? frontier)
            (>= (count came-from) max-states)
            (= (first (peek frontier)) goal-state))
      came-from
      (let [current (first (peek frontier))
            children (set (children-fn current))
            unvisited-children (clojure.set/difference children visited)
            heuristics (map (partial heuristic-fn goal-state) unvisited-children)
            new-frontier (reduce #(assoc %1 %2 (heuristic-fn goal-state %2)) (pop frontier) unvisited-children)
            new-came-from (reduce #(assoc %1 %2 current) came-from unvisited-children)
            new-visited (clojure.set/union children visited)]
        (recur new-frontier new-came-from new-visited)))))

(defn extract-path [came-from start-state goal-state]
  (loop [current-state goal-state
         path []]
    (cond
      (nil? current-state) nil
      (= current-state start-state) (reverse (conj path start-state))
      :else (recur (came-from current-state)
                   (conj path current-state)))))
