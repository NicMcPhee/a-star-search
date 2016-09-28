(ns problems.hanoi
  (require [clojure.set :as set]))

;;scardycat.clj was a practice file; this file is (hanoi.clj) our final submission

(defrecord State [towers])

(defn legal? [towers old-t new-t]
  (and (not (empty? (nth towers old-t)))
       (if (empty? (nth towers new-t))
         true
         (< (peek (nth towers old-t)) (peek (nth towers new-t))))))

(defn swap [towers old-t new-t]
  (let [disk (peek (nth towers old-t))
        new-old (pop (nth towers old-t))
        new-new (conj (nth towers new-t) disk)]
    (assoc (assoc towers old-t new-old) new-t new-new)))

(def towersinside [[5 4 3 2 1] [] []])
(def towers (->State towersinside))
(println towersinside)

(defn children [state]
  (for [[old new] [[0, 1] [1, 0], [0, 2], [1, 2] [2, 1], [2, 0]]
        :when (legal? (:towers state)  old new)]
    (->State (swap (:towers state) old new))))

(defn prefer-horizontal-cost [towers old-t new-t]
  (let [disk (peek (nth towers old-t))
        dist (Math/abs (- (nth towers old-t) (nth towers new-t)))]
    (println disk)
    (println dist)
    (+ disk dist)))

;; (defn prefer-horizontal-cost [cur nex]
;;   (for [[old new] [[0,1] , [0,2] , [1,2] , [1,0] , [2,0] , [2,1]]
;;         :when (= (peek (nth (:towers cur) old)) (peek (nth (:towers nex) new)))]
;;     (+ (peek (nth (:towers cur) old)) (Math/abs (- old new)))))

(def towersinside [[5 4 3 2 1] [] []])
(def towers (->State towersinside))
(println towersinside)


;; (defn prefer-horizontal-cost [s t]
;;   (let [s-x (first (:blank-position s))
;;         t-x (first (:blank-position t))]
;;     (inc (Math/abs (- s-x t-x)))))

;; (defn prefer-horizontal-cost [s t]
;;   (let [s-x (first (:blank-position s))
;;         t-x (first (:blank-position t))]
;;     (inc (Math/abs (- s-x t-x)))))
