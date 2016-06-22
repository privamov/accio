---
layout: page
title: Workflows & experiments definition
---

Workflows and experiments are easily defined as JSON.

## Workflow definition

A workflow is a JSON object formed of the following fields.

| Name | Description | Type | Required |
|:-----|:------------|:-----|:---------|
| id | A unique identifier for this workflow. By default, it is the file name without its extension. | string | false |
| meta.name | A human-readable name. | string | false |
| meta.owner | Person owning the workflow. It can include an email address between chevrons. | string | false |
| runs | Default number of runs for each node. It can be overriden on a per-node basis. | integer | false |
| graph | Nodes composing the workflow graph. The order in which nodes are defined does not matter. | object[] | true |
| graph[*].op | Operator name. | string | required |
| graph[*].name | Node name. By default it will take the operator name, with a numerical suffix appended if an operator appears multiple times. | string | optional |
| graph[*].inputs | Names of nodes acting as inputs of this node. | string[] | false |
| graph[*].params | Mapping between parameter names and values. All parameters without a default value should be specified. | object | false |
| graph[*].runs | Number of times this node should be ran. It is only useful for nodes producing metrics. If it is defined here, the value takes precedence over the global runs value. Results of different runs will be aggregated together (i.e., distributions resulting from all runs will be merged). | integer | false |
{: class="table table-striped"}

Here is a example of a simple workflow definition:

```json
{
  "id": "geoind_workflow",
  "meta": {
    "name": "Geo-indistinguishability workflow",
    "owner": "John Doe <john.doe@gmail.com>"
  },
  "graph": [
    {
      "op": "EventSource",
      "params": {
        "url": "/path/to/my/dataset"
      }
    },
    {
      "op": "GeoIndistinguishability",
      "inputs": ["EventSource"],
      "params": {
        "epsilon": "0.001"
      }
    },
    {
      "op": "PoisRetrieval",
      "name": "privacy",
      "inputs": ["EventSource", "GeoIndistinguishability"],
      "params": {
        "diameter": "200.meters",
        "duration": "15.minutes",
        "threshold": "100.meters"
      }
    },
    {
      "op": "SpatialDistortion",
      "name": "utility",
      "inputs": ["EventSource", "GeoIndistinguishability"]
    }
  ]
}
```

## Experiment definition

An experiment is a JSON object formed of the following fields.

| Name | Description | Type | Required |
|:-----|:------------|:-----|:---------|
| meta.name | A human-readable name. | string | false |
| meta.notes | Some free text notes. | string | false |
| meta.tags | Some tags. | string[] |  false |
| workflow | Path to a workflow definition file. It can be either a relative (starting with `./`), home relative (starting with `~`) or absolute (starting with `/`) path. | string | true |
| params | Mapping between fully qualified names of parameters to override and new values. | object | false|
| exploration | Exploration configuration. | object | false |
| exploration.params | Mapping between fully qualified names of parameters to override and domains of values. | object | true |
| optimization | Optimization configuration. | object | false |
| optimization.grid | Mapping between fully qualified names of parameters to override and domains of values. | object | true |
| optimization.iters | Number of steps per temperature value. | integer | false |
| optimization.contraction | Contraction of the domain when choosing a new value. | double | false |
| optimization.objectives | Optimization objectives. | object[] | true |
| optimization.objectives[*].type | Objective type: "minimize" or "optimize". | string | true |
| optimization.objectives[*].metric | Objective metric. It is the fully qualified name of an artifact. | string | true |
| optimization.objectives[*].threshold | Objective threshold. | double | false |
{: class="table table-striped"}

## Parameters

### Parameters values


### Parameters references

When you need to refer to a parameter, you have two options.
When inside the context of a node, you can use directly its name, for example `distance`.
When outside of the context of a node (e.g., when defining global parameters overrides), you must use its fully qualified name.
Fully qualified names are built with the node name, a slash '/' seperator and the parameter name, for example `PoisRetrieval:distance`.